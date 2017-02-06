'use strict';

describe('Controller Tests', function() {

    describe('Script Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockScript, MockPlano, MockPlugin;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockScript = jasmine.createSpy('MockScript');
            MockPlano = jasmine.createSpy('MockPlano');
            MockPlugin = jasmine.createSpy('MockPlugin');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Script': MockScript,
                'Plano': MockPlano,
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
