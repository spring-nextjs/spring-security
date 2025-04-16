INSERT INTO roles (id, name) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48321', 'ROLE_USER');
INSERT INTO roles (id, name) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48322', 'ROLE_MODERATOR');
INSERT INTO roles (id, name) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48323', 'ROLE_ADMIN');

INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440011', 'user:read');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440012', 'user:write');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440013', 'user:create');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440014', 'user:delete');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440015', 'moderator:read');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440016', 'moderator:write');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440017', 'moderator:create');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440018', 'moderator:delete');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440019', 'admin:read');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440020', 'admin:write');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440021', 'admin:create');
INSERT INTO permissions (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440022', 'admin:delete');

INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48321', '550e8400-e29b-41d4-a716-446655440011');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48321', '550e8400-e29b-41d4-a716-446655440012');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48321', '550e8400-e29b-41d4-a716-446655440013');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48321', '550e8400-e29b-41d4-a716-446655440014');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48322', '550e8400-e29b-41d4-a716-446655440015');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48322', '550e8400-e29b-41d4-a716-446655440016');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48322', '550e8400-e29b-41d4-a716-446655440017');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48322', '550e8400-e29b-41d4-a716-446655440018');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48323', '550e8400-e29b-41d4-a716-446655440019');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48323', '550e8400-e29b-41d4-a716-446655440020');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48323', '550e8400-e29b-41d4-a716-446655440021');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48323', '550e8400-e29b-41d4-a716-446655440022');

INSERT INTO users (id, username, email, password, role_id) VALUES ('550e8400-e29b-41d4-a716-446655440201', 'user', 'user@mail.com', '$argon2id$v=19$m=16384,t=2,p=1$+sNfYg6WWxWUS+c2FY1qQw$q+nWGRmQOt3tz3sqFfyNu546buc4O2tRei0eNVVrme0', '21b14b90-861f-4883-b457-ddf8d5f48321');
INSERT INTO users (id, username, email, password, role_id) VALUES ('550e8400-e29b-41d4-a716-446655440202', 'moderator', 'moderator@mail.com', '$argon2id$v=19$m=16384,t=2,p=1$+sNfYg6WWxWUS+c2FY1qQw$q+nWGRmQOt3tz3sqFfyNu546buc4O2tRei0eNVVrme0', '21b14b90-861f-4883-b457-ddf8d5f48322');
INSERT INTO users (id, username, email, password, role_id) VALUES ('550e8400-e29b-41d4-a716-446655440203', 'admin', 'admin@mail.com', '$argon2id$v=19$m=16384,t=2,p=1$+sNfYg6WWxWUS+c2FY1qQw$q+nWGRmQOt3tz3sqFfyNu546buc4O2tRei0eNVVrme0', '21b14b90-861f-4883-b457-ddf8d5f48323');