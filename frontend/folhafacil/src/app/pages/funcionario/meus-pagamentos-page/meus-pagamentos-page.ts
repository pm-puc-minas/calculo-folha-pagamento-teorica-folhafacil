import { Component, inject } from '@angular/core';
import { FolhaPagamentoService } from '../../../services/folha-pagento.service';
import { FolhaPagamentoResponseDTO } from '../../../models/folha-pagamento.model';

@Component({
  selector: 'app-meus-pagamentos-page',
  imports: [],
  templateUrl: './meus-pagamentos-page.html',
  styleUrl: './meus-pagamentos-page.css'
})
export class MeusPagamentosPage {
  service = inject(FolhaPagamentoService)

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.service.meusPagamentos().subscribe({
      next: (res: FolhaPagamentoResponseDTO[]) => {
        console.log(res)
      },
    })
  }
}
