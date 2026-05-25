import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TableComponent } from '../../../components/table/containers/table.component';
import { TotalizerComponent } from '../../../components/totalizer/containers/totalizer.component';
import { ModalComponent } from '../../../components/modal/containers/modal.component';
import { DeviceModalComponent } from '../components/device-modal/device-modal.component';
import { DevicesFacade } from '../facades/devices.facade';
import { DevicesApi } from '../apis/devices.api';
import { Device, MACHINES_COLUMNS } from '../models/devices.model';

@Component({
  imports: [
    CommonModule,
    TableComponent,
    TotalizerComponent,
    ModalComponent,
    DeviceModalComponent,
  ],
  selector: 'feature-devices',
  templateUrl: './devices.component.html',
  providers: [DevicesFacade, DevicesApi],
})
export class DevicesComponent implements OnInit {
  public open: boolean = false;
  public deviceFormGroup!: FormGroup;
  public columns = MACHINES_COLUMNS;

  constructor(
    public router: Router,
    private formBuilder: FormBuilder,
    public devicesFacade: DevicesFacade,
  ) {}

  ngOnInit(): void {
    this.deviceFormGroup = this.formBuilder.group({
      id: [null],
      name: [''],
      serialNumber: [''],
    });
    this.devicesFacade.getAllDevices();
  }

  openCreateDevice(): void {
    this.deviceFormGroup.reset({
      id: null,
      name: '',
      serialNumber: '',
    });
    this.open = true;
  }

  openEditDevice(device: Device): void {
    this.deviceFormGroup.patchValue(device);
    this.open = true;
  }

  submitDevice(): void {
    const { id, ...payload } = this.deviceFormGroup.value;
    if (id) {
      this.devicesFacade.updateDevice(id, payload);
    } else {
      this.devicesFacade.createDevice(payload);
    }
    this.open = false;
  }

  deleteDevice(device: Device): void {
    this.devicesFacade.deleteDevice(device.id);
  }
}
