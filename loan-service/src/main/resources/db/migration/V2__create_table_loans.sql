CREATE TABLE loanEntities (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  book_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  loan_date DATETIMEOFFSET NOT NULL,
  due_date DATETIMEOFFSET NOT NULL,
  returned_date DATETIMEOFFSET,
  status VARCHAR(50) NOT NULL,

  CONSTRAINT FK_loans_users
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);