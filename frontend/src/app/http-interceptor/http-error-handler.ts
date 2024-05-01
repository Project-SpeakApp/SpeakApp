import { finalize, tap } from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {AlertService} from "../shared/services/alert.service";

@Injectable()
export class HttpErrorHandler implements HttpInterceptor {
  constructor(private alertService: AlertService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const started = Date.now();
    let ok: string;

    return next.handle(req)
      .pipe(
        tap({
          next: (event) => (ok = event instanceof HttpResponse ? 'succeeded' : ''),
          error: (_error) => {
            ok = 'failed';
            this.handleHttpError(_error);
          }
        }),
        finalize(() => {
          const elapsed = Date.now() - started;
          const msg = `${req.method} "${req.urlWithParams}"
             ${ok} in ${elapsed} ms.`;
          if (ok === 'failed') console.error(msg);
          else console.log(msg);
        })
      );
  }

  handleHttpError(error: any) {
    if (error?.error?.errorMessages && error?.error?.errorMessages.length > 0) {
      for (const errorMessage of error.error.errorMessages) {
        this.alertService.showAlert(errorMessage, 'error');
      }
    }
    else {
      this.alertService.showAlert('Something went wrong...', 'error');
    }
  }
}
