import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TableComponent } from '../../../components/table/containers/table.component';
import { TotalizerComponent } from '../../../components/totalizer/containers/totalizer.component';
import { ModalComponent } from '../../../components/modal/containers/modal.component';
import { OrganizationModalComponent } from '../components/organization-modal/organization-modal.component';
import { OrganizationsFacade } from '../facades/organizations.facade';
import { OrganizationsApi } from '../apis/organizations.api';
import { Organization, COMPANIES_COLUMNS } from '../models/organizations.model';

@Component({
  imports: [
    CommonModule,
    TableComponent,
    TotalizerComponent,
    ModalComponent,
    OrganizationModalComponent,
  ],
  selector: 'feature-organizations',
  templateUrl: './organizations.component.html',
  providers: [OrganizationsFacade, OrganizationsApi],
})
export class OrganizationsComponent implements OnInit {
  public open: boolean = false;
  public organizationFormGroup!: FormGroup;
  public columns = COMPANIES_COLUMNS;

  constructor(
    public router: Router,
    private formBuilder: FormBuilder,
    public organizationsFacade: OrganizationsFacade,
  ) {}

  ngOnInit(): void {
    this.organizationFormGroup = this.formBuilder.group({
      id: [null],
      name: [''],
      cnpj: [''],
    });
    this.organizationsFacade.getAllOrganizations();
  }

  openCreateOrganization(): void {
    this.organizationFormGroup.reset({ id: null, name: '', cnpj: '' });
    this.open = true;
  }

  openEditOrganization(organization: Organization): void {
    this.organizationFormGroup.patchValue(organization);
    this.open = true;
  }

  submitOrganization(): void {
    const { id, ...payload } = this.organizationFormGroup.value;
    if (id) {
      this.organizationsFacade.updateOrganization(id, payload);
    } else {
      this.organizationsFacade.createOrganization(payload);
    }
    this.open = false;
  }

  deleteOrganization(organization: Organization): void {
    this.organizationsFacade.deleteOrganization(organization.id);
  }
}
