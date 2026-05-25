export interface Device {
  id: string;
  name: string;
  serialNumber: string;
  organizationId: string;
}

export const MACHINES_COLUMNS = [
  { name: 'id', label: 'ID' },
  { name: 'name', label: 'Name' },
  { name: 'serialNumber', label: 'Serial Number' },
  { name: 'organizationId', label: 'Organization' },
  { name: 'actions', label: 'Actions' },
];
