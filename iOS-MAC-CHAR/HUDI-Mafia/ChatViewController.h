//
//  ChatViewController.h
//  HUDI-Mafia
//
//  Created by YongJai on 2017. 4. 14..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <WebsocketStompKit.h>

@interface ChatViewController : UIViewController

@property (nonatomic, strong) NSMutableArray *userInfo;
@property (nonatomic, strong) NSMutableArray *accessUserInfo;
@property (nonatomic, strong) NSMutableArray *userReady;
@property (nonatomic, strong) NSMutableDictionary *chatMessage;
@property (nonatomic, strong) NSMutableArray *chatContent;
@property (nonatomic, strong) NSMutableArray *chatNick;

@property (nonatomic, strong) NSMutableArray *votedNick;

@property (weak, nonatomic) IBOutlet UITableView *chatTable;
@property (weak, nonatomic) IBOutlet UIButton *sendBtn;
@property (nonatomic, strong) NSString *roomNum;
@property (nonatomic, strong) NSString *roomTitle;
@property (nonatomic, strong) NSData *roomData;

@property (nonatomic, strong) NSTimer *countDownTimer;
@property (nonatomic) int secondsCount;
@property (nonatomic) int count;

@property (weak, nonatomic) IBOutlet UICollectionView *userCollection;

@end

