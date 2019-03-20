import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { DecisionTreeProvider } from '../../providers/decision-tree/decision-tree';
import { DecisionTree } from '../../entities/decision-tree';
import { Response } from '@angular/http';

@Component({
    selector: 'decision-tree',
    templateUrl: 'decision-tree.html',
})
export class DecisionTreeComponent {

    private decisionTree: DecisionTree;
    private color: string;
    private fromAvistamientoCreate: boolean;
    private finalizeFromLeafNode: boolean = false;

    constructor(
        private navParams: NavParams,
        private navCtrl: NavController,
        private decisionTreeProvider: DecisionTreeProvider) 
    {
        this.color = navParams.get('color');
        this.fromAvistamientoCreate = navParams.get('fromAvistamientoCreate');

        console.log(
            'decisionTree ' + JSON.stringify(navParams.get('decisionTreeRoot')), this.decisionTree
        );
    }

    ionViewDidLoad(): void {
        this.decisionTree = new DecisionTree();
        let treeID = this.navParams.get('treeID')

        this.decisionTreeProvider.getRoot(treeID).subscribe(
            (response: Response) => {
                this.decisionTree = new DecisionTree(response.json()[0]);
                this.nextNode(this.decisionTree);
            });
    }

    ionViewCanLeave(): boolean {
        let canLeave: boolean = this.decisionTree.parent == undefined || this.finalizeFromLeafNode;

        if (!canLeave) this.previuosNode();

        return canLeave;
    }

    previuosNode(): void {
        this.decisionTree = this.decisionTree.parent;
    }

    nextNode(decisionTree: DecisionTree): void {
        this.decisionTree = decisionTree;
        if (this.decisionTree.hasChildren) {
            this.decisionTreeProvider.getChildren(decisionTree.id).subscribe(
                (response: Response): void => {
                    this.decisionTree.children = DecisionTree.parse(response.json());
                },
                (error: any): void => {
                    console.log(DecisionTreeComponent.name + ' nextNode getChildren error ' + JSON.stringify(error));
                });
        }
    }

    isAnotherSpecies() {
        this.navCtrl.getPrevious().data.usoArbol = true;
        this.navCtrl.getPrevious().data.otraEspecie = true;
        this.navCtrl.pop();
    }

    closeView() {
        if (this.fromAvistamientoCreate) {
            this.navCtrl.getPrevious().data.name = this.decisionTree.name;
            this.navCtrl.getPrevious().data.binomialNomenclature = this.decisionTree.alias;
            this.navCtrl.getPrevious().data.description = this.decisionTree.description;
            this.navCtrl.getPrevious().data.usoArbol = true;

        }
        this.finalizeFromLeafNode = true;
        this.navCtrl.pop();
    }
}
