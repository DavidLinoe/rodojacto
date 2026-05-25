import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TableComponent } from '../../../components/table/containers/table.component';
import { TotalizerComponent } from '../../../components/totalizer/containers/totalizer.component';
import { ModalComponent } from '../../../components/modal/containers/modal.component';
import { OrganizationModalComponent } from '../components/company-modal/company-modal.component';
import { OrganizationsFacade } from '../facades/companies.facade';
import { OrganizationsApi } from '../apis/companies.api';
import { Organization, COMPANIES_COLUMNS } from '../models/companies.model';

@Component({
  imports: [
    CommonModule,
    TableComponent,
    TotalizerComponent,
    ModalComponent,
    OrganizationModalComponent,
  ],
  selector: 'feature-companies',
  templateUrl: './companies.component.html',
  providers: [OrganizationsFacade, OrganizationsApi],
})
export class OrganizationsComponent implements OnInit {
  public open: boolean = false;
  public companyFormGroup!: FormGroup;
  public columns = COMPANIES_COLUMNS;

  constructor(
    public router: Router,
    private formBuilder: FormBuilder,
    public companiesFacade: OrganizationsFacade,
  ) {}

  ngOnInit(): void {
    this.companyFormGroup = this.formBuilder.group({
      id: [null],
      name: [''],
      cnpj: [''],
    });
    this.companiesFacade.getAllOrganizations();
  }

  openCreateOrganization(): void {
    this.companyFormGroup.reset({ id: null, name: '', cnpj: '' });
    this.open = true;
  }

  openEditOrganization(company: Organization): void {
    this.companyFormGroup.patchValue(company);
    this.open = true;
  }

  submitOrganization(): void {
    const { id, ...payload } = this.companyFormGroup.value;
    if (id) {
      this.companiesFacade.updateOrganization(id, payload);
    } else {
      this.companiesFacade.createOrganization(payload);
    }
    this.open = false;
  }

  deleteOrganization(company: Organization): void {
    this.companiesFacade.deleteOrganization(company.id);
  }
}
