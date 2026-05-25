import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Device } from '../models/devices.model';

@Injectable()
export class DevicesApi {
  constructor(private apiService: ApiService) {}

  getAllDevices() {
    return this.apiService.get<Device[]>(`devices/all`);
  }

  createDevice(machine: Partial<Device>) {
    return this.apiService.post<Device>('devices', machine);
  }

  updateDevice(id: string, machine: Partial<Device>) {
    return this.apiService.put<Device>('devices', { id, ...machine });
  }

  deleteDevice(id: string) {
    return this.apiService.delete<Device>(`devices?id=${id}`);
  }
}
