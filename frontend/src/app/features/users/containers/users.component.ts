import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TableComponent } from '../../../components/table/containers/table.component';
import { TotalizerComponent } from '../../../components/totalizer/containers/totalizer.component';
import { ModalComponent } from '../../../components/modal/containers/modal.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CollaboratorModalComponent } from '../components/user-modal/user-modal.component';
import { CollaboratorsFacade } from '../facades/users.facade';
import { CollaboratorsApi } from '../apis/users.api';
import { Collaborator, USERS_COLUMNS } from '../models/users.model';

@Component({
  imports: [CommonModule, TableComponent, TotalizerComponent, ModalComponent, CollaboratorModalComponent],
  selector: 'feature-users',
  templateUrl: './users.component.html',
  providers: [CollaboratorsFacade, CollaboratorsApi],
})
export class CollaboratorsComponent implements OnInit {
  public open: boolean = false;
  public userFormGroup!: FormGroup;
  public columns = USERS_COLUMNS;

  constructor(
    public router: Router,
    private formBuilder: FormBuilder,
    public usersFacade: CollaboratorsFacade,
  ) {}

  ngOnInit(): void {
    this.userFormGroup = this.formBuilder.group({
      id: [null],
      name: [''],
      email: [''],
      role: [''],
    });
    this.usersFacade.getAllCollaborators();
  }

  openCreateCollaborator(): void {
    this.userFormGroup.reset({ id: null, name: '', email: '', role: '' });
    this.open = true;
  }

  openEditCollaborator(user: Collaborator): void {
    this.userFormGroup.patchValue(user);
    this.open = true;
  }

  submitCollaborator(): void {
    const { id, ...payload } = this.userFormGroup.value;
    if (id) {
      this.usersFacade.updateCollaborator(id, payload);
    } else {
      this.usersFacade.createCollaborator(payload);
    }
    this.open = false;
  }

  deleteCollaborator(user: Collaborator): void {
    this.usersFacade.deleteCollaborator(user.id);
  }
}
