import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CollaboratorsApi } from '../apis/collaborators.api';
import { Collaborator } from '../models/collaborators.model';
import { TokenService } from '../../../services/token.service';

@Injectable()
export class CollaboratorsFacade {
  public collaborators$: BehaviorSubject<Collaborator[]> = new BehaviorSubject<Collaborator[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(private collaboratorsApi: CollaboratorsApi, private tokenService: TokenService) {}

  getAllCollaborators(): void {
    const token = this.tokenService.decodeToken();
    if (!token?.organizationId) {
      console.error('Organization ID is missing in the token.');
      return;
    }
    this.collaboratorsApi.getAllCollaborators(token?.organizationId).subscribe((response) => {
      if (response.data) {
        this.collaborators$.next(response.data);
        this.count$.next(response.count || response.data.length);
      }
    });
  }

  createCollaborator(collaborator: Partial<Collaborator>): void {
    this.collaboratorsApi.createCollaborator(collaborator).subscribe(() => {
      this.getAllCollaborators();
    });
  }

  updateCollaborator(id: number, collaborator: Partial<Collaborator>): void {
    this.collaboratorsApi.updateCollaborator(id, collaborator).subscribe(() => {
      this.getAllCollaborators();
    });
  }

  deleteCollaborator(id: number): void {
    this.collaboratorsApi.deleteCollaborator(id).subscribe(() => {
      this.getAllCollaborators();
    });
  }
}
