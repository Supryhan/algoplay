-- player:
-- player_id | name   | join_date
-- ---------------------------------
--  1        | John   | 07.12.2010
--  2        | Kate   | 17.01.2015
--  4        | Rajiv  | 05.03.2007
--
-- payment:
-- payment_id | player_id | game_id | amount
-- ------------------------------------------
--  100       | 2         | 4       | 3.5
--  102       | 2         | 5       | 20
--  103       | 2         | 4       | 12.5
--  107       | 4         | 5       | 2
--
-- SELECT query to get name, avg_amount
--
-- join_date >= 01.01.2009
-- avg_amount > 5

SELECT
    player.name,
    AVG(payment.amount) AS avg_amount
FROM
    player
        INNER JOIN
    payment ON player.player_id = payment.player_id
WHERE
        player.join_date >= '2009-01-01'
GROUP BY
    player.player_id, player.name
HAVING
        AVG(payment.amount) > 5;