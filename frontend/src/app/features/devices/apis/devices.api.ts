import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Device } from '../models/devices.model';

@Injectable()
export class DevicesApi {
  constructor(private apiService: ApiService) {}

  getAllDevices() {
    return this.apiService.get<Device[]>('devices');
  }

  getDeviceById(id: number) {
    return this.apiService.get<Device>(`devices/${id}`);
  }

  createDevice(device: Partial<Device>) {
    return this.apiService.post<Device>('devices', device);
  }

  updateDevice(id: number, device: Partial<Device>) {
    return this.apiService.put<Device>(`devices/${id}`, device);
  }

  deleteDevice(id: number) {
    return this.apiService.delete<Device>(`devices/${id}`);
  }
}
