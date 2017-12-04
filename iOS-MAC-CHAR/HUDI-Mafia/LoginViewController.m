//
//  LoginViewController.m
//  Mafia
//
//  Created by YongJai on 2017. 3. 25..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import "LoginViewController.h"

@interface LoginViewController ()

@property (weak, nonatomic) IBOutlet UITextField *userEmailTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextField;
@property (weak, nonatomic) IBOutlet UILabel *loginError;

@end

@implementation LoginViewController

- (void)viewDidLoad {
   [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void) hideLabel{
    _loginError.hidden = YES;
}

- (IBAction)clickLoginBtn:(id)sender {
    
    NSString *userEmail = _userEmailTextField.text;
    NSString *userPassword = _passwordTextField.text;
    NSString *URLString = @"http://1.255.56.109:8080/api/login";
    NSURL *URL = [NSURL URLWithString:URLString];
    NSMutableURLRequest *loginRequest = [NSMutableURLRequest requestWithURL:URL];
    
    [loginRequest setValue:@"application/json" forHTTPHeaderField: @"content-Type"];
    [loginRequest setHTTPMethod:@"POST"];
    
    NSDictionary *loginData = @{
                                @"email" : userEmail,
                                @"password" : userPassword
                                };
    

    [loginRequest setHTTPBody:[NSJSONSerialization dataWithJSONObject:loginData options:kNilOptions error:NULL]];
    
    NSHTTPURLResponse * loginResponse;
    NSError * loginError;
    
    NSData * loginResultData = [NSURLConnection sendSynchronousRequest:loginRequest returningResponse:&loginResponse error:&loginError];
    NSLog(@"login response = %ld", loginResponse.statusCode);
    NSDictionary * dataDictionary = [NSJSONSerialization JSONObjectWithData:loginResultData options:NSJSONReadingMutableContainers error:nil];
    NSLog(@"login result = %@", dataDictionary);
    
    NSArray *dataDicArray = [dataDictionary allValues];
    
    if([dataDicArray containsObject:@"Ok"]){
         [self performSegueWithIdentifier:@"roomSegue" sender:self];
    } else{
        _loginError.hidden = NO;
        [self performSelector:@selector(hideLabel) withObject:nil afterDelay:1.5];
    }
}

@end
