import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  imports: [CommonModule, ReactiveFormsModule],
  selector: 'feature-organizations-component-organization-modal',
  templateUrl: './organization-modal.component.html',
})
export class OrganizationModalComponent {
  public form = input<FormGroup>(new FormGroup({}));
}
