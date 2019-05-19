CREATE TYPE asset_enum as ENUM ('Tangible', 'Intangible', 'Current', 'Fixed', 'Operating',
'NotOperating');

CREATE TABLE assets_table(
  asset_id        TEXT PRIMARY KEY NOT NULL,
  company_id      TEXT NOT NULL,
  asset_type      asset_enum NOT NULL,
  asset_name      TEXT NOT NULL,
  comment         VARCHAR(100));


INSERT INTO assets_table (asset_id,  company_id , asset_type, asset_name, comment)
VALUES ('e041a71e-cc34-4779-87ac-bfc54e57755f', '2f341f84-accd-43a6-abbc-70d41de2c9d3', 'Tangible','investments', 'excellent');
INSERT INTO assets_table  (asset_id,  company_id , asset_type, asset_name, comment)
VALUES ('b060bf0e-3af8-4fcb-b72d-374f449833ad', '2f341f84-accd-43a6-abbc-70d41de2c9d3', 'Intangible','inventory', 'good condition');
INSERT INTO assets_table  (asset_id,  company_id , asset_type, asset_name, comment)
VALUES ('fc3c3e7a-ee2e-47fa-8532-ade4ad0d691c', '562f5ed0-b0bf-40cf-85c1-e560631057b8', 'Current','land', 'Very good');
