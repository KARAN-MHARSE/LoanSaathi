import { platformBrowser } from '@angular/platform-browser';
import 'bootstrap/dist/css/bootstrap.min.css';
import { AppModule } from './app/app-module';

platformBrowser().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true,
})
  .catch(err => console.error(err));
