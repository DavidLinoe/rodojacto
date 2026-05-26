export interface Device {
  id: number;
  model: string;
  assetTag: string;
  organizationId: number;
  createdAt?: string;
}

export const DEVICES_COLUMNS = [
  { name: 'id', label: 'ID' },
  { name: 'model', label: 'Modelo' },
  { name: 'assetTag', label: 'Patrimônio' },
  { name: 'actions', label: 'Ações' },
];
