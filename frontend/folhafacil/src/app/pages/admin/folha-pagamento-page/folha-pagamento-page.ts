import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { SortEvent } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { FloatLabel } from 'primeng/floatlabel';
import { DatePicker } from 'primeng/datepicker';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActionsService } from '../../../services/actions.service';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { FolhaPagamentoService } from '../../../services/folha-pagento.service';

@Component({
  selector: 'app-folha-pagamento-page',
  imports: [
    CommonModule,
    TableModule,
    TagModule,
    ButtonModule,
    Dialog,
    FloatLabel,
    DatePicker,
    FormsModule,
    ReactiveFormsModule,
    ConfirmPopupModule
  ],
  templateUrl: './folha-pagamento-page.html',
  styleUrl: './folha-pagamento-page.css',
  providers: [ConfirmationService]
})
export class FolhaPagamentoPage {
  service = inject(FolhaPagamentoService)
  confirmationService = inject(ConfirmationService)
  actions = inject(ActionsService)

  gerar(event: Event) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Deseja gerar/atualizar? Essa ações pode demorar (Referente ao mês atual)',
      accept: () => {
        this.service.gerar().subscribe({
          next: () =>{
            this.actions.success("Folha gerada/atualizada com sucesso")
          },
          error: () =>{

          }
        })
      },
      reject: () => {
      }
    });
  }
}
