'use strict';

describe('Controller Tests', function() {

    describe('Stage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStage, MockAdresse, MockEntreprise, MockResponsable, MockEnseignant, MockFiliere, MockEtudiant;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStage = jasmine.createSpy('MockStage');
            MockAdresse = jasmine.createSpy('MockAdresse');
            MockEntreprise = jasmine.createSpy('MockEntreprise');
            MockResponsable = jasmine.createSpy('MockResponsable');
            MockEnseignant = jasmine.createSpy('MockEnseignant');
            MockFiliere = jasmine.createSpy('MockFiliere');
            MockEtudiant = jasmine.createSpy('MockEtudiant');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Stage': MockStage,
                'Adresse': MockAdresse,
                'Entreprise': MockEntreprise,
                'Responsable': MockResponsable,
                'Enseignant': MockEnseignant,
                'Filiere': MockFiliere,
                'Etudiant': MockEtudiant
            };
            createController = function() {
                $injector.get('$controller')("StageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'taagliProjectApp:stageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
