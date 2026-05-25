import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { DevicesApi } from '../apis/devices.api';
import { Device } from '../models/devices.model';

@Injectable()
export class DevicesFacade {
  public devices$: BehaviorSubject<Device[]> = new BehaviorSubject<Device[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(private devicesApi: DevicesApi) {}

  getAllDevices(): void {
    this.devicesApi.getAllDevices().subscribe((response) => {
      if (response.data) {
        this.devices$.next(response.data);
        this.count$.next(response.count || response.data.length);
      }
    });
  }

  createDevice(machine: Partial<Device>): void {
    this.devicesApi.createDevice(machine).subscribe(() => {
      this.getAllDevices();
    });
  }

  updateDevice(id: string, machine: Partial<Device>): void {
    this.devicesApi.updateDevice(id, machine).subscribe(() => {
      this.getAllDevices();
    });
  }

  deleteDevice(id: string): void {
    this.devicesApi.deleteDevice(id).subscribe(() => {
      this.getAllDevices();
    });
  }
}
