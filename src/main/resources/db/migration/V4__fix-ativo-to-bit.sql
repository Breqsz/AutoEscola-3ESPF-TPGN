ALTER TABLE instrutores
  MODIFY ativo BIT(1) NOT NULL DEFAULT b'1';


UPDATE instrutores SET ativo = b'1' WHERE ativo IS NULL OR ativo = 1;
