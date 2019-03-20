import { Component, ViewChild } from '@angular/core';
import { NavParams, NavController } from 'ionic-angular';
import { MidemeResultComponent } from '../mideme-result/mideme-result';
import { FormGroup } from '@angular/forms';
import 'rxjs/add/operator/map';
import { AlertController } from 'ionic-angular';
import { ControlBase } from '../../app/shared/utilidades/forms/control-base';
import { ControlsService } from '../../app/shared/utilidades/forms/controls.service';
import { DynamicFormComponent } from '../../app/shared/utilidades/forms/dynamic-form.component';
import { MidemeProvider } from '../../providers/mideme/mideme';
import { WriteKeyExpr } from '@angular/compiler';
import { elementAt } from 'rxjs/operators';


@Component({
	selector: 'mideme-calculator',
	templateUrl: 'mideme-calculator.html'
})
export class MidemeCalculatorComponent {
	@ViewChild(DynamicFormComponent)
	private DynamicFormComponent: DynamicFormComponent;
	controls: ControlBase<any>[];
	form: FormGroup;
	submitted: any;

	color: string;
	informacion: any[] = [];

	constructor(private navParams: NavParams
		, private navCtrl: NavController,
		private controlsService: ControlsService,
		private alertCtrl: AlertController,
		private midemeService: MidemeProvider

	) {
		this.color = navParams.get('color');
		this.form = new FormGroup({});
	}

	ngOnInit() {
		let form: any[] = [];
		this.midemeService.getForms().subscribe((response: any) => {
			this.informacion = response[0].preguntas;
			form = []
			response[0].preguntas.forEach((pregunta: any,i) => {
				let opc: any[] = [];
				if (pregunta.opcPreguntas.length > 0) {

					pregunta.opcPreguntas.forEach((opcion) => {
						opc.push({
							key: opcion.clave,
							label: opcion.valor
						})
					});
					form.push({
						display: "selected",
						name: "Seleccionar",
						required: pregunta.obligatorio,
						selected: false,
						title: pregunta.descripcion,
						type: pregunta.tipoPregunta.nombre,
						options: opc,
						placeholder: opc[0].label,
						multiple: false,
					})

				}
				else{
					let placeholder: string = '';
					if(pregunta.tipoPregunta.nombre == 'Numero'){
						placeholder = "0"
					}
					form.push({
						display: "selected",
						name: "Seleccionar",
						required: pregunta.obligatorio,
						selected: false,
						title: pregunta.descripcion,
						type: pregunta.tipoPregunta.nombre,
						options: opc,
						placeholder: placeholder,
						multiple: false,
					})

				}
				// console.log(i)
				// if(i + 1 == response[0].preguntas.length){
				// 	console.log('entro', form);
					// this.controls = this.controlsService.getControls(form);
				// }
			});
			this.controls = this.controlsService.getControls(form);

		})
		form = [
			{
				display: "selected",
				name: "Seleccionar2",
				required: false,
				selected: false,
				title: "Titulo de categoria",
				type: "Texto"
			},
			{
				display: "selected",
				name: "Seleccionar",
				required: false,
				selected: false,
				title: "Pregunta numero 1",
				type: "Lista",
				options: [{ key: 1, label: 1 }, { key: 2, label: 2 }],
				placeholder: "0",
			},
		]
			this.controls = this.controlsService.getControls(form);


		this.form.valueChanges
			.subscribe(val => {
				this.submitted = val;
			});
	}
	crearForm() {

	}

	ionViewWillEnter() {
	}

	submitForm(event) {
		console.log('informacion11', event)
		console.log('informacion22', this.submitForm)
		console.log('informacion333', this.form)


		let alert = this.alertCtrl.create({
			title: 'Success!',
			subTitle: 'Your form was successfully submitted!',
			buttons: ['OK']
		});
		alert.present();
	}

	goToResult(): void {
		this.navCtrl.push(MidemeResultComponent, {
			color: this.color,
		});
	}

}
