import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  imports: [CommonModule, ReactiveFormsModule],
  selector: 'feature-devices-component-device-modal',
  templateUrl: './device-modal.component.html',
})
export class DeviceModalComponent {
  public form = input<FormGroup>(new FormGroup({}));
}
