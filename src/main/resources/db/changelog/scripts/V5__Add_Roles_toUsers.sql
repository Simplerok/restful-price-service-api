INSERT INTO users_to_roles(users_id, roles_id)
VALUES ((SELECT users.id FROM users WHERE users.username = 'Revolver666'),1),
    ((SELECT users.id FROM users WHERE users.username = 'KiloTolik21'),1),
    ((SELECT users.id FROM users WHERE users.username = 'RikiTikiTak'),1),
    ((SELECT users.id FROM users WHERE users.username = 'RikiTikiTak'),2);