DROP TABLE jcaptcha_t
GO

CREATE TABLE jcaptcha_image_t (
    timeMillis	bigint NULL,
    hashcode	bigint NULL,
    locale   	varchar(25) NULL,
    captcha  	other NULL
    )
GO

CREATE INDEX I_LOCALES
    ON jcaptcha_image_t(locale)
GO

CREATE UNIQUE INDEX I_PK
    ON jcaptcha_image_t(timeMillis,hashcode)
GO
