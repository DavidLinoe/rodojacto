export interface Organization {
  id: string;
  name: string;
  cnpj: string;
}

export const COMPANIES_COLUMNS = [
  { name: 'id', label: 'ID' },
  { name: 'name', label: 'Name' },
  { name: 'cnpj', label: 'CNPJ' },
  { name: 'actions', label: 'Actions' },
];
