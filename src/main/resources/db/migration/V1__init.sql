CREATE TABLE organizations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    corporate_name VARCHAR(255) NOT NULL,
    registration_code VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_organizations_registration_code (registration_code)
) ENGINE=InnoDB;

CREATE TABLE collaborators (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    access_level VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    organization_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_collaborators_email (email),
    CONSTRAINT fk_collaborators_organization
        FOREIGN KEY (organization_id) REFERENCES organizations (id)
) ENGINE=InnoDB;

CREATE TABLE devices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    model VARCHAR(255) NOT NULL,
    asset_tag VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    organization_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_devices_asset_tag (asset_tag),
    CONSTRAINT fk_devices_organization
        FOREIGN KEY (organization_id) REFERENCES organizations (id)
) ENGINE=InnoDB;