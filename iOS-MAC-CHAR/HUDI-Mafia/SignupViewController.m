//
//  SignupViewController.m
//  Mafia
//
//  Created by YongJai on 2017. 3. 26..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import "SignupViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface SignupViewController ()
@property (weak, nonatomic) IBOutlet UITextField *nickNameTextField;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordConfirmTextField;

@property (weak, nonatomic) IBOutlet UILabel *nickNameError;
@property (weak, nonatomic) IBOutlet UILabel *emailError;
@property (weak, nonatomic) IBOutlet UILabel *passwordError;
@property (weak, nonatomic) IBOutlet UILabel *nickNameNoTextError;

@property (weak, nonatomic) IBOutlet UIButton *signUpBtn;

@end

@implementation SignupViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.signUpBtn.layer.cornerRadius = 6.0f;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (IBAction)signUpBtnClick:(id)sender {
    NSString *nickName  = _nickNameTextField.text;
    NSString *email = _emailTextField.text;
    NSString *password = _passwordTextField.text;
    NSString *passwordConfirm = _passwordConfirmTextField.text;
    
    if([password isEqualToString:passwordConfirm]){
    NSString *URLString = @"http://1.255.56.109:8080/api/signup";
    NSURL *URL = [NSURL URLWithString:URLString];
    NSMutableURLRequest *signupRequest = [NSMutableURLRequest requestWithURL:URL];
    
    [signupRequest setValue:@"application/json" forHTTPHeaderField: @"content-Type"];
    [signupRequest setHTTPMethod:@"POST"];
    
    NSDictionary *signupData = @{
                                @"email" : email,
                                @"password" : password,
                                @"nickname" : nickName,
                                };
    
    [signupRequest setHTTPBody:[NSJSONSerialization dataWithJSONObject:signupData options:kNilOptions error:NULL]];
    
    NSHTTPURLResponse * signupResponse;
    NSError * signupError;
    
    NSData * signupResultData = [NSURLConnection sendSynchronousRequest:signupRequest returningResponse:&signupResponse error:&signupError];
    NSLog(@"signup response = %ld", signupResponse.statusCode);
    NSDictionary * dataDictionary = [NSJSONSerialization JSONObjectWithData:signupResultData options:NSJSONReadingMutableContainers error:nil];
    NSLog(@"signup result = %@", dataDictionary);
        NSArray *dataDicArray = [dataDictionary allValues];
        
        if([dataDicArray containsObject:@"Ok"]){
            [self performSegueWithIdentifier:@"signUpSegue" sender:self];
        } else if([dataDicArray containsObject:@"EmailExists"]){
            _emailError.hidden = NO;
        }
    } else {
        _passwordError.hidden = NO;
    }
}

@end
