//
//  LobbyViewController.h
//  HUDI-Mafia
//
//  Created by YongJai on 2017. 4. 14..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LobbyViewController : UIViewController

@property (nonatomic, strong) NSMutableArray *roomTitle;
@property (nonatomic, strong) NSMutableArray *roomNum;
@property (nonatomic, strong) NSMutableArray *userCount;
@property (nonatomic, strong) NSMutableArray *createdRoomId;
@property (nonatomic, strong) NSMutableDictionary *roomData;
@property (nonatomic, strong) NSMutableDictionary *enterDataDictionary;

@property (weak, nonatomic) IBOutlet UITableView *roomList;

@property (nonatomic, strong) NSString *gotNickName;
@property (nonatomic, strong) NSString *roomId;

@property (weak, nonatomic) IBOutlet UILabel *userNickName;
@property (weak, nonatomic) IBOutlet UIView *createRoomView;
@property (weak, nonatomic) IBOutlet UITextField *createRoomTitleTextField;
@property (weak, nonatomic) IBOutlet UITableView *roomListTable;

@end
