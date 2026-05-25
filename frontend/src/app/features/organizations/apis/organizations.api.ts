import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Organization } from '../models/organizations.model';

@Injectable()
export class OrganizationsApi {
  constructor(private apiService: ApiService) {}

  getAllOrganizations(collaboratorId: string) {
    return this.apiService.get<Organization[]>(`organizations?id=${collaboratorId}`);
  }

  createOrganization(organization: Partial<Organization>) {
    return this.apiService.post<Organization>('organizations', organization);
  }

  updateOrganization(id: string, organization: Partial<Organization>) {
    return this.apiService.put<Organization>('organizations', { id, ...organization });
  }

  deleteOrganization(id: string) {
    return this.apiService.delete<Organization>(`organizations?id=${id}`);
  }
}
