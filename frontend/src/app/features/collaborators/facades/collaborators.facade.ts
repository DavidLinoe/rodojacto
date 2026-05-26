import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CollaboratorsApi } from '../apis/collaborators.api';
import { Collaborator } from '../models/collaborators.model';
import { TokenService } from '../../../services/token.service';

@Injectable()
export class CollaboratorsFacade {
  public collaborators$: BehaviorSubject<Collaborator[]> = new BehaviorSubject<Collaborator[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(
    private collaboratorsApi: CollaboratorsApi,
    private tokenService: TokenService,
  ) {}

  getAllCollaborators(): void {
    this.collaboratorsApi.getAllCollaborators().subscribe((response) => {
      if (response.data) {
        this.collaborators$.next(response.data);
        this.count$.next(response.count ?? response.data.length);
      }
    });
  }

  createCollaborator(collaborator: Partial<Collaborator>): void {
    const organizationId =
      collaborator.organizationId ?? this.tokenService.decodeToken()?.organizationId;
    if (!organizationId) {
      console.error('organizationId ausente no token.');
      return;
    }
    const payload: Partial<Collaborator> = { ...collaborator, organizationId };
    this.collaboratorsApi.createCollaborator(payload).subscribe((response) => {
      if (response.data) {
        const updated = [...this.collaborators$.getValue(), response.data];
        this.collaborators$.next(updated);
        this.count$.next(updated.length);
      }
    });
  }

  updateCollaborator(id: number, collaborator: Partial<Collaborator>): void {
    const organizationId =
      collaborator.organizationId ?? this.tokenService.decodeToken()?.organizationId;
    const payload: Partial<Collaborator> = { ...collaborator, organizationId };
    this.collaboratorsApi.updateCollaborator(id, payload).subscribe((response) => {
      if (response.data) {
        const updated = this.collaborators$
          .getValue()
          .map((item) => (item.id === id ? response.data! : item));
        this.collaborators$.next(updated);
      }
    });
  }

  deleteCollaborator(id: number): void {
    this.collaboratorsApi.deleteCollaborator(id).subscribe(() => {
      const updated = this.collaborators$.getValue().filter((item) => item.id !== id);
      this.collaborators$.next(updated);
      this.count$.next(updated.length);
    });
  }
}
