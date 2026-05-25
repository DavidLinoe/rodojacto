import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { OrganizationsApi } from '../apis/organizations.api';
import { Organization } from '../models/organizations.model';
import { TokenService } from '../../../services/token.service';

@Injectable()
export class OrganizationsFacade {
  public organizations$: BehaviorSubject<Organization[]> = new BehaviorSubject<Organization[]>([]);
  public count$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(
    private organizationsApi: OrganizationsApi,
    private tokenService: TokenService,
  ) {}

  getAllOrganizations(): void {
    const collaboratorId = this.tokenService.decodeToken()?.collaboratorId ?? '';
    this.organizationsApi.getAllOrganizations(collaboratorId).subscribe((response) => {
      if (response.data) {
        this.organizations$.next(response.data);
        this.count$.next(response.count || response.data.length);
      }
    });
  }

  createOrganization(organization: Partial<Organization>): void {
    this.organizationsApi.createOrganization(organization).subscribe(() => {
      this.getAllOrganizations();
    });
  }

  updateOrganization(id: string, organization: Partial<Organization>): void {
    this.organizationsApi.updateOrganization(id, organization).subscribe(() => {
      this.getAllOrganizations();
    });
  }

  deleteOrganization(id: string): void {
    this.organizationsApi.deleteOrganization(id).subscribe(() => {
      this.getAllOrganizations();
    });
  }
}
