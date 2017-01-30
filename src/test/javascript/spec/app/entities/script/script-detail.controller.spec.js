'use strict';

describe('Controller Tests', function() {

    describe('Script Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockScript, MockAtividade, MockExecutarPlano, MockPlugin;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockScript = jasmine.createSpy('MockScript');
            MockAtividade = jasmine.createSpy('MockAtividade');
            MockExecutarPlano = jasmine.createSpy('MockExecutarPlano');
            MockPlugin = jasmine.createSpy('MockPlugin');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Script': MockScript,
                'Atividade': MockAtividade,
                'ExecutarPlano': MockExecutarPlano,
                'Plugin': MockPlugin
            };
            createController = function() {
                $injector.get('$controller')("ScriptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autobotApp:scriptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
