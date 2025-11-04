import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { TextareaModule } from 'primeng/textarea';
import { FloatLabel } from 'primeng/floatlabel';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActionsService } from '../../../services/actions.service';
import { HoraExtraDTO, HoraExtraResponseDTO } from '../../../models/horaextra.model';
import { HoraExtraService } from '../../../services/hora-extra.service';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-minhas-horas-extras-page',
  imports: [
    CommonModule,
    TableModule,
    TagModule,
    ButtonModule,
    Dialog,
    TextareaModule,
    FloatLabel,
    FormsModule,
    ReactiveFormsModule,
    ConfirmPopupModule
  ],
  templateUrl: './minhas-horas-extras-page.html',
  styleUrl: './minhas-horas-extras-page.css',
  providers: [ConfirmationService]
})
export class MinhasHorasExtrasPage {
  actions = inject(ActionsService)
  service = inject(HoraExtraService)
  cdr = inject(ChangeDetectorRef)
  confirmationService = inject(ConfirmationService)

  horaextraForm: FormGroup;

  isModal: boolean = false

  horasExtras: HoraExtraResponseDTO[] = []

  constructor(private fb: FormBuilder) {
    this.horaextraForm = this.fb.group({
      descricao: [],
    });
  }

  ngOnInit() {
    this.buscar()
  }

  salvar() {
    this.service.inicar(this.getHoraExtraForm()).subscribe({
      next: () => {
        this.closeModal()
        this.actions.success("Hora extra iniciada")
        this.buscar()
      }
    })
  }

  buscar() {
    this.service.minhasHoras().subscribe({
      next: (res: HoraExtraResponseDTO[]) => {
        if (res.length > 0) {
          console.log(res)
          this.horasExtras = [...res]
          this.cdr.markForCheck();
        } else {
          this.actions.info("Nenhum registro encontrado")
        }
      }
    })
  }

  finalizar(event: Event, id: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Deseja finalizar?',
      accept: () => {
        this.service.finalizar(id).subscribe({
          next: () =>{
            this.actions.info("Hora extra finalizada")
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

  novo() {
    this.isModal = true
  }

  getHoraExtraForm() {
    const i: HoraExtraDTO = this.horaextraForm.value

    return i
  }

  resetHoraExtraForm() {
    this.horaextraForm = this.fb.group({
      mensagem: [],
    });
  }

  isValidForm() {
    return this.horaextraForm.valid
  }

  closeModal() {
    this.isModal = false
    this.resetHoraExtraForm()
  }

  getServerityStatus(s: string) {
    switch (s) {
      case 'EM_ANDAMENTO':
        return 'info';
      case 'CONCLUIDA':
        return 'success';
      default:
        return 'danger'
    }
  }
}
