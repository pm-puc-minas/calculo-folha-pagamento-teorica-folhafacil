import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BeneficioDTO, BeneficioResponseDTO } from '../../../models/beneficio.model';
import { BeneficioService } from '../../../services/beneficio.service';
import { ActionsService } from '../../../services/actions.service';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-beneficios-page',
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    Dialog,
    InputTextModule,
    FloatLabel,
    FormsModule,
    ReactiveFormsModule,
    ConfirmPopupModule
  ],
  templateUrl: './beneficios-page.html',
  styleUrl: './beneficios-page.css',
  providers: [ConfirmationService]
})
export class BeneficiosPage {
  service = inject(BeneficioService);
  actions = inject(ActionsService);
  cdr = inject(ChangeDetectorRef)
  confirmationService = inject(ConfirmationService)

  beneficios: BeneficioResponseDTO[] = []

  isModal: boolean = false
  isEdit: boolean = false

	beneficioForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.beneficioForm = this.fb.group({
      id: [],
      nome: [],
    });
	}

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.service.buscar().subscribe({
      next : (res : BeneficioResponseDTO[]) =>{
        if(res.length == 0){
          this.actions.info("Nenhum Registro encontrado")
        }else{
          this.beneficios = [...res]
          this.cdr.markForCheck();
        }
      },
      error : () =>{
      }
    })
  }

  edit(b: BeneficioResponseDTO){
    this.isEdit = true
    this.isModal = true
    this.preencherBeneficioForm(b)
  }

  delete(event: Event, id: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Deseja deletar?',
      accept: () => {
        this.service.excluir(id).subscribe({
          next: () =>{
            this.actions.info("Benefício deletado com sucesso")
            this.buscar()
          },
          error: () =>{

          }
        })
      },
      reject: () => {
      }
    });
  }

  verFuncionarios(id: number){
    console.log(id)
  }

  resetBeneficioForm(){
    this.beneficioForm = this.fb.group({
      id: [],
      nome: [],
    });
  }

  preencherBeneficioForm(b: BeneficioResponseDTO){
    this.beneficioForm = this.fb.group({
      id: [b.id],
      nome: [b.nome],
    });
  }

  getBeneficioForm(){
    const i : BeneficioDTO = this.beneficioForm.value

    return i;
  }

  isValidForm(): boolean {
    return this.beneficioForm.valid
  }

  salvar(){
    this.service.salvar(this.getBeneficioForm())
    .subscribe({
      next : (res: any) =>{
        this.closeModal()
        this.actions.success('Benefício salvo com sucesso')
        this.buscar()
      },
      error: () =>{
      }
    })
  }

  novo(){
    this.isModal = true
  }

  closeModal(){
    this.resetBeneficioForm()
    this.isModal = false
    this.isEdit = false
  }
}
