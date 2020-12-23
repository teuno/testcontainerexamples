CREATE TABLE IF NOT EXISTS tbl_category
(
    category_id        SERIAL       PRIMARY KEY      NOT NULL,
    name               VARCHAR(125) UNIQUE NOT NULL,
    parent_category_id INTEGER                  NULL
    );

INSERT INTO tbl_category (name)
VALUES ('category1');

INSERT INTO tbl_category (name, parent_category_id)
VALUES ('category2', 1),
       ('category3', 1),
       ('category4', 1),
       ('category5', 4),
       ('category6', 4),
       ('category7', 5),
       ('category8', 5),
       ('category9', 5),
       ('category10', 9),
       ('category11', 3),
       ('category12', 4),
       ('category13', 4);

CREATE TABLE IF NOT EXISTS tbl_product
(
    product_id  SERIAL UNIQUE            NOT NULL,
    name        VARCHAR(125) PRIMARY KEY NOT NULL,
    price       NUMERIC(7, 2)            NOT NULL DEFAULT 0,
    category_id INTEGER                  NOT NULL,
    FOREIGN KEY (category_id) REFERENCES tbl_category (category_id)
    );


DO '
BEGIN
    FOR counter IN 1..400
        LOOP
            INSERT INTO tbl_product (name, price, category_id)
            VALUES (CONCAT(''product'', counter::varchar(125)),
                    (100 * random())::numeric(7, 2), --random cash max 100
                    floor(random() * (13 - 1 + 1) + 1)); -- random category min 1 max 13
        END LOOP;
END; ' LANGUAGE plpgsql;
