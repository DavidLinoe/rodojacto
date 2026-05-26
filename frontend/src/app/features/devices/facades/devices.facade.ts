import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { DevicesApi } from '../apis/devices.api';
import { Device } from '../models/devices.model';
import { TokenService } from '../../../services/token.service';

@Injectable()
export class DevicesFacade {
  public devices$: BehaviorSubject<Device[]> = new BehaviorSubject<Device[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(
    private devicesApi: DevicesApi,
    private tokenService: TokenService,
  ) {}

  getAllDevices(): void {
    this.devicesApi.getAllDevices().subscribe((response) => {
      if (response.data) {
        this.devices$.next(response.data);
        this.count$.next(response.count ?? response.data.length);
      }
    });
  }

  createDevice(device: Partial<Device>): void {
    const organizationId =
      device.organizationId ?? this.tokenService.decodeToken()?.organizationId;
    if (!organizationId) {
      console.error('organizationId ausente no token.');
      return;
    }
    const payload: Partial<Device> = { ...device, organizationId };
    this.devicesApi.createDevice(payload).subscribe((response) => {
      if (response.data) {
        const updated = [...this.devices$.getValue(), response.data];
        this.devices$.next(updated);
        this.count$.next(updated.length);
      }
    });
  }

  updateDevice(id: number, device: Partial<Device>): void {
    const organizationId =
      device.organizationId ?? this.tokenService.decodeToken()?.organizationId;
    const payload: Partial<Device> = { ...device, organizationId };
    this.devicesApi.updateDevice(id, payload).subscribe((response) => {
      if (response.data) {
        const updated = this.devices$
          .getValue()
          .map((item) => (item.id === id ? response.data! : item));
        this.devices$.next(updated);
      }
    });
  }

  deleteDevice(id: number): void {
    this.devicesApi.deleteDevice(id).subscribe(() => {
      const updated = this.devices$.getValue().filter((item) => item.id !== id);
      this.devices$.next(updated);
      this.count$.next(updated.length);
    });
  }
}
