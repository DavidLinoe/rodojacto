export type AccessLevel = 'MANAGER' | 'OPERATOR';

export interface Collaborator {
  id: number;
  fullName: string;
  email: string;
  accessLevel: AccessLevel;
  organizationId: number;
  password?: string;
  createdAt?: string;
}

export const COLLABORATORS_COLUMNS = [
  { name: 'id', label: 'ID' },
  { name: 'fullName', label: 'Nome' },
  { name: 'email', label: 'Email' },
  { name: 'accessLevel', label: 'Nível de Acesso' },
  { name: 'actions', label: 'Ações' },
];

export const ACCESS_LEVEL_OPTIONS: { value: AccessLevel; label: string }[] = [
  { value: 'MANAGER', label: 'Gerente' },
  { value: 'OPERATOR', label: 'Operador' },
];
