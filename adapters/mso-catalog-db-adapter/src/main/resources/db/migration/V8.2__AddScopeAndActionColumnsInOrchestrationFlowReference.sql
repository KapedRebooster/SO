use catalogdb;

ALTER TABLE orchestration_flow_reference
ADD SCOPE VARCHAR (200) DEFAULT NULL,
ADD ACTION VARCHAR (200) DEFAULT NULL;