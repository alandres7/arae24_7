import { Component, OnInit, OnDestroy } from '@angular/core';
import { NavController, NavParams, AlertController, ActionSheetController, Platform } from 'ionic-angular';
import { DecisionTreeProvider } from '../../providers/decision-tree/decision-tree';
import { DecisionTree } from '../../entities/decision-tree';
import { Response } from '@angular/http';
import { Common } from '../../app/shared/utilidades/common';
import { CuidameReportComponent } from '../cuidame-report/cuidame-report';

@Component({
    selector: 'decision-tree-vigia',
    templateUrl: 'decision-tree-vigia.html'
})
export class DecisionTreeVigiaComponent {
    private decisionTree: any;
    private color: string;
    private fromVigiaCreate: boolean;
    private lastNode: boolean = false;
    private municipio: string;
    private infoReport: string;
    private pictures: any;
    private flagDropdown: boolean;
    private coordenadas: any;
    private listOptions: any[] = [];
    private dropDown: string = 'Usted observa...'
    private finalizeFromLeafNode: boolean = false;
    private actionSheet: any;


    constructor(
        private navParams: NavParams,
        private navCtrl: NavController,
        private decisionTreeProvider: DecisionTreeProvider,
        private common: Common,
        private actionSheetCtrl: ActionSheetController,
        private alertCtrl: AlertController,
        private platform: Platform
    ) {
        this.color = navParams.get('color');
        this.infoReport = navParams.get('info');
        this.pictures = navParams.get('pictures');
        this.municipio = navParams.get('municipio');
        this.fromVigiaCreate = navParams.get('fromVigiaCreate');
        this.coordenadas = navParams.get('coordenadas');
    }

    ionViewDidLoad(): void {
        this.decisionTree = new DecisionTree();
        let treeID = this.navParams.get('treeID');
        // Obtenemos el arbol de desicion correspondiente
        this.decisionTreeProvider.getRoot(treeID).subscribe((response: Response) => {
            this.decisionTree = DecisionTree.parse(response.json());
            if (this.decisionTree[0].flgChildrenDropdown == true) {
                this.flagDropdown = true;
            }
            else {
                this.flagDropdown = false;
            }
            this.decisionTree = this.decisionTree[0];
            this.decisionTreeProvider.getChildren(this.decisionTree.id).subscribe((childrens) => {
                this.decisionTree.children = DecisionTree.parse(childrens.json());
                this.decisionTree.hasChildren = true;
            })
        });
    }

    previuosNode(): void {
        this.listOptions = [];
        if (this.decisionTree.parent.flgChildrenDropdown == true) {
            this.flagDropdown = true;
            this.dropDown = 'Usted observa...'
        }
        else {
            this.dropDown = this.decisionTree.parent.name
        }
        this.decisionTree = this.decisionTree.parent;
    }

    nextNode(decisionTree: DecisionTree): void {
        this.decisionTree = decisionTree;
        if (!this.decisionTree.flgChildrenDropdown || this.decisionTree.flgChildrenDropdown == undefined) {
            this.flagDropdown = false;
        }
        if (this.decisionTree.flgChildrenDropdown == true) {
            this.flagDropdown = true;
        }

        if (this.decisionTree.hasChildren == true) {
            this.decisionTreeProvider.getChildren(decisionTree.id).subscribe(
                (response: Response): void => {
                    console.log('el seleccionado11111', response.json())
                    this.decisionTree.children = DecisionTree.parse(response.json())
                    if (this.flagDropdown == true) {
                        this.presentAction();
                    }
                    if (this.decisionTree.children.length == 1) {
                        this.lastNode = true;
                        this.decisionTree.children = [];
                        this.decisionTree.children.push(decisionTree);
                        console.log('el seleccionado222222', response.json()[0].idAutoridadCompetente)

                        if (response.json()[0].idAutoridadCompetente == null) {
                            //Si es la ultima opcion se abre alerta notificando al usuario si esta seguro de generar el reporte
                            this.common.basicAlePrtCss('Haz seleccionado una opción', 'Estamos a un paso de terminar, confirma que los datos sean correctos y se radicará la denuncia.', 'warning sGenRep btnSolo', 'Ver reporte')
                                .then((response) => {
                                    //Si el usuario selecciona 'Aceptar' se abre modal con el resumen del reporte a postear
                                    let finishReportModal = this.common.createModal(CuidameReportComponent,
                                        { color: this.color, municipio: this.municipio, infoReport: this.infoReport, pictures: this.pictures, coordenadas: this.coordenadas, arbol: decisionTree }, { cssClass: 'mapaUbicacion' });
                                    finishReportModal.onDidDismiss((response): void => {
                                        if (response == 'ok') {
                                            this.closeView();
                                        }
                                    })

                                    finishReportModal.present();
                                }).catch((response) => {
                                    // this.common.dismissAlert();
                                });
                        }
                        else {
                            if (response.json()[0].idAutoridadCompetente != null){

                                this.common.confirmAlertCss('Haz seleccionado una opción', '¿Estás seguro? una vez aceptes se generará el reporte.', 'warning sGenRep')
                                    .then((response) => {
                                        //Si el usuario selecciona 'Aceptar' se abre modal con el resumen del reporte a postear
                                        let finishReportModal = this.common.createModal(CuidameReportComponent,
                                            { color: this.color, municipio: this.municipio, infoReport: this.infoReport, pictures: this.pictures, coordenadas: this.coordenadas, arbol: decisionTree, reportar: true }, { cssClass: 'mapaUbicacion' });
                                        finishReportModal.onDidDismiss((response): void => {
                                            if (response == 'ok') {
                                                this.closeView();
                                            }
                                        })
    
                                        finishReportModal.present();
                                    }).catch((response) => {
                                        // this.common.dismissAlert();
                                    });
                            }
                        }
                    }
                },
                (error: any): void =>
                    console.log(
                        DecisionTreeVigiaComponent.name +
                        ' nextNode getChildren error ' +
                        JSON.stringify(error),
                    ),
            );
        }
    }

    isAnotherSpecies() {
        this.navCtrl.getPrevious().data.usoArbol = true;
        this.navCtrl.getPrevious().data.otraEspecie = true;
        this.navCtrl.pop();
    }

    closeView() {
        if (this.fromVigiaCreate) {
            this.navCtrl.getPrevious().data.name = this.decisionTree.name;
            this.navCtrl.getPrevious().data.description = this.decisionTree.description;
            this.navCtrl.getPrevious().data.usoArbol = true;
        }
        this.finalizeFromLeafNode = true;
        this.navCtrl.pop();
    }

    presentAction(): void {
        //Si los hijos del nodo tienen 'flagHijosDropdow = true' se colocan en la lista del actionSheet
        if (this.decisionTree.flgChildrenDropdown == true) {
            this.listOptions = [];
            let listChildrens: any = this.decisionTree.children;
            listChildrens.forEach((element: any, i) => {
                this.listOptions.push({
                    text: element.name,
                    handler: () => {
                        this.dropDown = element.name;
                        element.parent = this.decisionTree;
                        this.nextNode(element);
                    }
                });
                if (i == listChildrens.length - 1) {
                    this.actionSheet = this.actionSheetCtrl.create({
                        title: 'Usted observa...',
                        buttons: this.listOptions
                    });
                    this.actionSheet.present();
                }
            });
        }
    }




    ionViewCanLeave(): any {
        if (this.actionSheet) {
            this.actionSheet.dismiss();
        }
        let canLeave: boolean = this.decisionTree.parent == undefined || this.finalizeFromLeafNode;
        if (!canLeave) this.previuosNode();
        return canLeave;
    }
}

