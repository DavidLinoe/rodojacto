import { Injectable } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { Organization } from '../models/organizations.model';

@Injectable()
export class OrganizationsApi {
  constructor(private apiService: ApiService) {}

  getAllOrganizations() {
    return this.apiService.get<Organization[]>('organizations');
  }

  getOrganizationById(id: number) {
    return this.apiService.get<Organization>(`organizations/${id}`);
  }

  createOrganization(organization: Partial<Organization>) {
    return this.apiService.post<Organization>('organizations', organization);
  }

  updateOrganization(id: number, organization: Partial<Organization>) {
    return this.apiService.put<Organization>(`organizations/${id}`, organization);
  }

  deleteOrganization(id: number) {
    return this.apiService.delete<Organization>(`organizations/${id}`);
  }
}
