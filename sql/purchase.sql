select
    a.id,
    a.latitude,
    a.longitude,
    a.title,
    a.place,
    a.date,
    a.price,
    a.denominator,
    a.numerator,
    a.status,
    (
        select
            count(*)
        from
            nthing.like b
        where true
            and b.user_id = 3
            and b.purchase_id = a.id
    ) as is_liked
from
    nthing.purchase as a

# insert
INSERT INTO nthing.purchase
    (
     title,
     description,
     latitude,
     longitude,
     date,
     denominator,
     numerator,
     status,
     price,
     place,
     updated_at,
     manager_id
     )
VALUES
    (
    '양말 20켤레 공동구매',
    '신촌역 앞에서 양말 20켤레씩 싸게 파는데 전 5켤레만 필요해서 나머지 15켤레 구매하실분들 구합니다',
     '2.2',
     '2.2',
     '2023-07-11 18:00:00',
     '5',
     '20',
     '0',
     '2000',
     '신촌역 2번출구 앞',
     '2023-07-07 08:33:22',
     '1'
     );