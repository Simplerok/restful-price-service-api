-- Магнит
INSERT INTO prices(value, shops_id, products_id)
VALUES ('35.70',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Хлеб бородинский'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Сормовский хлеб'))),
       ('42.55',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Батон белый'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Сормовский хлеб'))),
       ('37.40',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Хлеб бородинский'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Арзамаский хлеб'))),
       ('44.35',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Батон белый'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Арзамаский хлеб'))),
       ('40.85',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Хлеб бородинский'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Каравай'))),
       ('49.35',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Батон белый'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Каравай'))),
       ('68.65',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Молоко'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Городецкий молочный завод'))),
       ('74.50',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Творог'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Городецкий молочный завод'))),
       ('76.95',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Молоко'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Домик в деревне'))),
       ('89.65',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Творог'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Домик в деревне'))),
       ('73.45',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Молоко'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Простоквашино'))),
       ('78.35',
        (SELECT shops.id FROM shops WHERE shops.name = 'Магнит'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Творог'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Простоквашино')));

-- Пятерочка
INSERT INTO prices(value, shops_id, products_id)
VALUES ('36.45',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Хлеб бородинский'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Сормовский хлеб'))),
       ('38.95',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Батон белый'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Сормовский хлеб'))),
       ('42.50',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Хлеб бородинский'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Арзамаский хлеб'))),
       ('39.35',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Батон белый'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Арзамаский хлеб'))),
       ('44.35',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Хлеб бородинский'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Каравай'))),
       ('51.50',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Батон белый'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Каравай'))),
       ('64.55',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Молоко'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Городецкий молочный завод'))),
       ('71.30',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Творог'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Городецкий молочный завод'))),
       ('79.30',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Молоко'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Домик в деревне'))),
       ('82.25',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Творог'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Домик в деревне'))),
       ('77.45',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Молоко'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Простоквашино'))),
       ('75.40',
        (SELECT shops.id FROM shops WHERE shops.name = 'Пятерочка'),
        (SELECT products.id
         FROM products
         WHERE products.name = 'Творог'
	       AND products.brands_id = (SELECT brands.id FROM brands WHERE brands.name = 'Простоквашино')));
