export interface Device {
  id: string;
  name: string;
  serialNumber: string;
  companyId: string;
}

export const MACHINES_COLUMNS = [
  { name: 'id', label: 'ID' },
  { name: 'name', label: 'Name' },
  { name: 'serialNumber', label: 'Serial Number' },
  { name: 'companyId', label: 'Organization' },
  { name: 'actions', label: 'Actions' },
];
