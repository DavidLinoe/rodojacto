import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TableComponent } from '../../../components/table/containers/table.component';
import { TotalizerComponent } from '../../../components/totalizer/containers/totalizer.component';
import { ModalComponent } from '../../../components/modal/containers/modal.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CollaboratorModalComponent } from '../components/collaborator-modal/collaborator-modal.component';
import { CollaboratorsFacade } from '../facades/collaborators.facade';
import { CollaboratorsApi } from '../apis/collaborators.api';
import { Collaborator, COLLABORATORS_COLUMNS } from '../models/collaborators.model';

@Component({
  imports: [
    CommonModule,
    TableComponent,
    TotalizerComponent,
    ModalComponent,
    CollaboratorModalComponent,
  ],
  selector: 'feature-collaborators',
  templateUrl: './collaborators.component.html',
  providers: [CollaboratorsFacade, CollaboratorsApi],
})
export class CollaboratorsComponent implements OnInit {
  public open: boolean = false;
  public collaboratorFormGroup!: FormGroup;
  public columns = COLLABORATORS_COLUMNS;

  constructor(
    public router: Router,
    private formBuilder: FormBuilder,
    public collaboratorsFacade: CollaboratorsFacade,
  ) {}

  ngOnInit(): void {
    this.collaboratorFormGroup = this.formBuilder.group({
      id: [null],
      fullName: [''],
      email: [''],
      password: [''],
      accessLevel: ['OPERATOR'],
    });
    this.collaboratorsFacade.getAllCollaborators();
  }

  openCreateCollaborator(): void {
    this.collaboratorFormGroup.reset({
      id: null,
      fullName: '',
      email: '',
      password: '',
      accessLevel: 'OPERATOR',
    });
    this.open = true;
  }

  openEditCollaborator(collaborator: Collaborator): void {
    this.collaboratorFormGroup.patchValue({
      id: collaborator.id,
      fullName: collaborator.fullName,
      email: collaborator.email,
      accessLevel: collaborator.accessLevel,
      password: '',
    });
    this.open = true;
  }

  submitCollaborator(): void {
    const { id, password, ...rest } = this.collaboratorFormGroup.value;
    const payload: Partial<Collaborator> = { ...rest };
    if (password) payload.password = password;
    if (id) {
      this.collaboratorsFacade.updateCollaborator(id, payload);
    } else {
      this.collaboratorsFacade.createCollaborator(payload);
    }
    this.open = false;
  }

  deleteCollaborator(collaborator: Collaborator): void {
    this.collaboratorsFacade.deleteCollaborator(collaborator.id);
  }
}
