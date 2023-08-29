package data.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.dto.CollegeDto;
import data.mapper.CollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RestController
public class CollegeController {

    @Autowired
    CollegeMapper collegeMapper;

    @Value("${kakao_key}")
    private String kakao_key;

    @Value("${univ_key}")
    private String univ_key;

    // id를 기반 학교 검색
    @GetMapping("/college/{id}")
    public CollegeDto selectCollegeById(@PathVariable Integer id) {
        return collegeMapper.selectCollegeById(id);
    }
    // 검색 키워드에 따른 학교 검색
    @GetMapping("/college")
    public List<CollegeDto> selectCollegeList(@RequestParam(required = false) String search_keyword){
        return collegeMapper.selectCollegeList(search_keyword);
    }

    // 대학명, 주소 저장
    @PostMapping("/college")
    public int insertCollegeList() throws JsonProcessingException {

        int result = 0;
        // 대학명을 가져오는 api.
        // 주소 데이터가 없는 경우 카카오 api를 추가로 호출하여 정보를 채워 넣는다.
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey="+ univ_key +"&svcType=api&svcCode=SCHOOL&contentType=json&gubun=univ_list&thisPage=1&perPage=10000";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        // JSON 파싱
        JsonNode rootNode = new ObjectMapper().readTree(response.getBody());
        JsonNode contentNode = rootNode.path("dataSearch").path("content");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode1 = null;
        JsonNode contentNode1 = null;

        HashMap<String, Object> map;
        for(JsonNode node : contentNode) {
            String schoolName = node.path("schoolName").asText();
            String address = node.path("adres").asText();
            Double latitude = 0.0;
            Double longitude = 0.0;

            /*
            * 3가지 경우
            * 1. 명칭, 주소가 있을 때 => 주소를 기반으로 좌표 검색
            * 2. 명칭은 있고 주소는 없을 떄 => 명칭 기반으로 주소 및 좌표 검색
            * 2-1. 명칭 기반 주소로 좌표가 검색되지 않을 때 => 명칭으로 주소 및 좌표 검색?
            * */

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakao_key);

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // 주소가 있으면 주소를 기반으로 x(경도), y(위도)좌표 저장
            if (!address.equals("")) { // 1의 경우
                resourceUrl = "https://dapi.kakao.com/v2/local/search/address?query="+ address;
                response = restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class);
                rootNode1 = mapper.readTree(response.getBody());
                contentNode1 = rootNode1.path("documents");
                if (contentNode1.size() > 0) {
                    JsonNode campus = contentNode1.get(0);
                    latitude =  Double.parseDouble(campus.path("y").asText());
                    longitude =  Double.parseDouble(campus.path("x").asText());
                }
                // 코드를 합칠 경우, 좌표는 나오는데, 주소는 없는 경우가 있어서 우선 나눠둠.
            } else { // 2의 경우
                resourceUrl = "https://dapi.kakao.com/v2/local/search/keyword?query=" + schoolName;
                response = restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class);
                rootNode1 = mapper.readTree(response.getBody());
                contentNode1 = rootNode1.path("documents");
                if (contentNode1.size() > 0) {
                    JsonNode campus = contentNode1.get(0);
                    address = campus.path("road_address_name").asText();
                    latitude =  Double.parseDouble(campus.path("y").asText());
                    longitude =  Double.parseDouble(campus.path("x").asText());
                }
            }
            System.out.println("====================================");
            System.out.println(schoolName);
            System.out.println(address);
            System.out.println(latitude);
            System.out.println(longitude);

            map = new HashMap<>();
            map.put("name", schoolName);
            map.put("address", address);
            map.put("latitude", latitude);
            map.put("longitude", longitude);

            collegeMapper.insertCollege(map);
            // insert
            result++;
        }
        return result;
    }

}
