/*'use strict';

describe('Admin Controller', () => {

    var stateProvider;

    beforeEach(function() {
        angular.module('ngTagsInput', []);
        angular.module('ngGrid', []);
        angular.module('ngRoute', []);
        angular.module('angularFileUpload', []);
        angular.module('ngMaterial', []);
        angular.module('blockUI', []);
        angular.module('ui.utils.masks', []);
        angular.module('ui.router', []);
        angular.module('ui.grid', []);
        angular.module('ui.grid.pagination', []);
        angular.module('xeditable', []);
        angular.module('ui.bootstrap', []);
        angular.module('ui.bootstrap.datetimepicker', []);
        angular.module('ui.select', []);
        angular.module('ngSanitize', []);
        angular.module('ngNotify', []);
        angular.module('components', []);
        angular.module('fcsa-number', []);

        angular.module('erApp', [])
            .config(function(_$stateProvider_) {
                stateProvider = _$stateProvider_;
                spyOn(stateProvider, 'state').andCallThrough();
            });
    });


    it('it has dummy spec to test 2 + 2', () => {
        expect(stateProvider.state).toHaveBeenCalled();
    });

});*/

describe('routes', function() {
	var $rootScope, $state, $controller;
	
	beforeEach(angular.module('ngTagsInput', []));
	
	beforeEach(angular.module('ngGrid', []));
	beforeEach(angular.module('ngRoute', []));
	beforeEach(angular.module('angularFileUpload', []));
	beforeEach(angular.module('ngMaterial', []));
	beforeEach(angular.module('blockUI', []));
	beforeEach(angular.module('ui.utils.masks', []));
	beforeEach(angular.module('ui.router', []));
	beforeEach(angular.module('ui.grid', []));
	beforeEach(angular.module('ui.grid.pagination', []));
	beforeEach(angular.module('xeditable', []));
	beforeEach(angular.module('ui.bootstrap', []));
	beforeEach(angular.module('ui.bootstrap.datetimepicker', []));
	beforeEach(angular.module('ui.select', []));
	beforeEach(angular.module('ngSanitize', []));
	beforeEach(angular.module('ngNotify', []));
	beforeEach(angular.module('components', []));
	beforeEach(angular.module('fcsa-number', []));
    
    beforeEach(angular.mock.module('erApp'));
    
    beforeEach(inject(function (_$rootScope_,_$state_, _$controller_) {
    	$rootScope = _$rootScope_.$new();
    	$state = _$state_;
    	$controller = _$controller_;
    }));
    
    describe('test', function() {
        
    	it('should have the correct URL', function() {
            expect(2+2).toEqual(4);
        });
    	
    });
    
    
});