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

@property (weak, nonatomic) IBOutlet UIButton *nickNameConfirmBtn;
@property (weak, nonatomic) IBOutlet UIButton *emailConfirmBtn;

@end

@implementation SignupViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    //버튼
    self.nickNameConfirmBtn.layer.cornerRadius = 6.0f;
    self.emailConfirmBtn.layer.cornerRadius = 6.0f;
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)nickNameConfirmClick:(id)sender {
    NSString *nickName = _nickNameTextField.text;
    //이미 있는 닉네임일 경우
    if([nickName isEqualToString:@"yongjai"]){
        _nickNameError.hidden = NO;
    } else{
        _nickNameError.hidden = YES;
    }
    //닉네임 텍스트에 아무것도 없을 경우
    if([nickName isEqualToString:@""]){
        _nickNameNoTextError.hidden = NO;
    } else{
        _nickNameNoTextError.hidden = YES;
    }
}

- (IBAction)emailConfirmClick:(id)sender {
    NSString *email = _emailTextField.text;
    if([email isEqualToString:@"mafia@naver.com"]){
        _emailError.hidden = NO;
    } else{
        _emailError.hidden = YES;
    }
}

- (IBAction)signUpBtnClick:(id)sender {
    NSString *password = _passwordTextField.text;
    NSString *passwordConfirm = _passwordConfirmTextField.text;

    if(password == passwordConfirm){
        _passwordError.hidden = YES;
    } else{
        _passwordError.hidden = NO;
    }
    if(_nickNameError.hidden == YES && _emailError.hidden == YES && _passwordError.hidden && _nickNameNoTextError.hidden == YES){
       [self performSegueWithIdentifier:@"signUpSegue" sender:self];
    }
}
//텍스트에 아무것도 적지 않았을 경우

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
