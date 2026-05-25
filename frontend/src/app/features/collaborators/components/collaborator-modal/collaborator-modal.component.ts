import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  imports: [CommonModule, ReactiveFormsModule],
  selector: 'feature-collaborators-component-collaborator-modal',
  templateUrl: './collaborator-modal.component.html',
})
export class CollaboratorModalComponent {
  public form = input<FormGroup>(new FormGroup({}));
}
