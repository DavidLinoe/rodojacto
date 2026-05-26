export interface Organization {
  id: number;
  corporateName: string;
  registrationCode: string;
  createdAt?: string;
}

export const ORGANIZATIONS_COLUMNS = [
  { name: 'id', label: 'ID' },
  { name: 'corporateName', label: 'Razão Social' },
  { name: 'registrationCode', label: 'CNPJ' },
  { name: 'actions', label: 'Ações' },
];
