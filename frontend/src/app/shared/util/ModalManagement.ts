export class ModalManagement {
  static open(modalId: string): void {
    const currentWidth = document.body.offsetWidth;
    const header = document.getElementById('header') as HTMLElement;
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    const navbar = document.getElementById('navbar') as HTMLElement;

    if (modal) {
      modal.showModal();
      document.body.style.overflow = "hidden";
      const scrollBarWidth = document.body.offsetWidth - currentWidth;
      header.style.width = `calc(100% - ${scrollBarWidth}px)`;
      if (currentWidth < 768) {
        navbar.style.width = `calc(100% - ${scrollBarWidth}px)`;
      }
      document.body.style.marginRight = `${scrollBarWidth}px`;
    }
  }

  static close(modalId: string): void {
    const modal = document.getElementById(modalId) as HTMLDialogElement;
    const header = document.getElementById('header') as HTMLElement;
    const navbar = document.getElementById('navbar') as HTMLElement;
    if (modal) {
      modal.close();
      document.body.style.overflow = "auto";
      document.body.style.marginRight = "";
      header.style.position = '';
      header.style.width = '100%';
      navbar.style.width = '';
    }
  }
}
